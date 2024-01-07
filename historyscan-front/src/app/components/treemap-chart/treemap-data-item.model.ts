export class TreemapDataItem {
  constructor(
    public filename: string,
    public path: string,
    public parent: string,
    public score: number,
  ) {
  }
}
