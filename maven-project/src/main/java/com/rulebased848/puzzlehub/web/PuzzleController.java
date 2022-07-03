package com.rulebased848.puzzlehub.web;

import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import com.rulebased848.puzzlehub.domain.*;

@RestController
public class PuzzleController {
    @Autowired
    private PuzzleRepository repo;

    @RequestMapping("/search")
    public String searchPuzzles(@RequestBody String json) throws ParseException {
        JSONObject jo = null;
        try {
            jo = (JSONObject)(new JSONParser().parse(json));
        } catch (ClassCastException | ParseException e) {
            return "{\"ok\":false}";
        }

        var itemNum_ = jo.get("itemNum");
        if (itemNum_ == null) {
            return "{\"ok\":false}";
        }
        int itemNum = 0;
        try {
            itemNum = (int)(long)itemNum_;
        } catch (ClassCastException e) {
            return "{\"ok\":false}";
        }

        var page_ = jo.get("page");
        if (page_ == null) {
            return "{\"ok\":false}";
        }
        int page = 0;
        try {
            page = (int)(long)page_;
        } catch (ClassCastException e) {
            return "{\"ok\":false}";
        }

        var pageRequest = PageRequest.of(page - 1, itemNum, Sort.by(Sort.Direction.DESC, "createdAt", "id"));
        var result = repo.getPuzzlesWithPaging(pageRequest).getContent();

        jo = new JSONObject();
        jo.put("ok", true);
        jo.put("number", repo.getCountOfPuzzles());
        var ja = new JSONArray();
        for (Puzzle p : result) {
            var m = new LinkedHashMap(3);
            m.put("username", p.getUser().getUsername());
            m.put("content", (JSONArray)(new JSONParser().parse(p.getContent())));
            m.put("createdAt", p.getCreatedAt().toString());
            ja.add(m);
        }
        jo.put("puzzles", ja);

        return jo.toJSONString();
    }
}