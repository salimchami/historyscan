import {CurrentCodebase} from "./current-codebase.model";

export class CurrentCodebases {
    constructor(public codebases: Array<CurrentCodebase>,
                public _links: any) {
    }
}
