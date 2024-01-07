export class CodebaseUtils {
    static toUrl(codebaseUrl: string, name: string, branch: string) {
        const urlPrefix = codebaseUrl.substring(0, codebaseUrl.lastIndexOf('.git'));
        return `${urlPrefix}/blob/${branch}/${name}`;
    }
}
