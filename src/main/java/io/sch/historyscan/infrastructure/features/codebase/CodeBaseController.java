package io.sch.historyscan.infrastructure.features.codebase;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/codebases")
public class CodeBaseController {

    private final CodeBaseApplication codebaseApplication;

    public CodeBaseController(CodeBaseApplication codebaseApplication) {
        this.codebaseApplication = codebaseApplication;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddedCodebaseDTO> add(@RequestBody CodeBaseToAddDTO codeBaseToAddDTO) {
        var addedCodeBase = codebaseApplication.clone(codeBaseToAddDTO);
        return new ResponseEntity<>(addedCodeBase, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CodebaseDTO>> currentCodeBases() {
        return ResponseEntity.ok(List.of(new CodebaseDTO("", "")));
    }
}
