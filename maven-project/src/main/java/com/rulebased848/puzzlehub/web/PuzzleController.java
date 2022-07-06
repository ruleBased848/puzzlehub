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
    public String searchPuzzles(@RequestBody SearchRequest req) throws ParseException {
        var pageRequest = PageRequest.of(req.getPage() - 1, req.getItemNum(), Sort.by(Sort.Direction.DESC, "createdAt", "id"));
        var result = repo.getPuzzlesWithPaging(pageRequest).getContent();

        var jo = new JSONObject();
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