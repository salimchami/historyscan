export class NetworkLink {

  constructor(
    public path: string,
    public weight: number,
  ) {
  }

  static of(link: NetworkLink) {
    return new NetworkLink(
      link.path,
      link.weight,
    );
  }
}
