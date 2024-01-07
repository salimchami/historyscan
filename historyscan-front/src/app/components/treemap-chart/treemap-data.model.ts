import {TreemapDataItem} from "./treemap-data-item.model";

export class TreemapData {
  public static ROOT_NAME: string = 'Global';

  constructor(public title: string,
              public description: string,
              public data: Array<TreemapDataItem>) {
  }
}
