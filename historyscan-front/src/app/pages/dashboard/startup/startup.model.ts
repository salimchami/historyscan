import {HateoasModel} from "../../../shared/models/hateoas.model";

export class Startup extends HateoasModel {
    constructor(
        public message: string,
    ) {
        super();
    }
}
