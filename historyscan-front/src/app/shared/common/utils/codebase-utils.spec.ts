import {CodebaseUtils} from "./codebase-utils";

describe('CodebaseUtils', () => {
    it('should be true', () => {
        expect(CodebaseUtils.toUrl("https://github.com/mycodebase.git", "CodebaseUtils.spec.ts", "main"))
            .toEqual("https://github.com/mycodebase/blob/main/CodebaseUtils.spec.ts");
    });
});
