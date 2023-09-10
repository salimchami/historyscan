package io.sch.historyscan.infrastructure.features.codebase;

import io.sch.historyscan.infrastructure.features.codebase.clone.AddedCodebaseDTO;
import io.sch.historyscan.infrastructure.features.codebase.clone.CodeBaseToAddDTO;
import io.sch.historyscan.infrastructure.features.codebase.info.CodebaseDTO;
import io.sch.historyscan.infrastructure.features.codebase.list.CurrentCodebasesDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "/{codebaseName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CodebaseDTO> findCodeBase(@PathVariable("codebaseName") String codebaseName) {
        return codebaseApplication.findCodeBase(codebaseName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<CurrentCodebasesDTO> currentCodeBases() {
        return ResponseEntity.ok(codebaseApplication.currentCodeBases());
    }
}
