package io.sch.historyscan.web;

import io.sch.historyscan.web.codebase.CodeBaseToAddDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/codebases")
public class CodeBaseController {

    @PostMapping("/add")
    public ResponseEntity<AddedCodebaseDTO> add(@RequestBody CodeBaseToAddDTO codeBaseToAddDTO) {
        return new ResponseEntity<>(new AddedCodebaseDTO(), HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<CodebaseDTO>> currentCodeBases() {
        return ResponseEntity.ok(List.of(new CodebaseDTO("", "")));
    }
}
