export class CodebaseHistoryCommitInfo {
    constructor(
        public hash: string,
        public author: string,
        public date: Date,
        public shortMessage: string
    ) {
    }
}
