export class CodebaseToAdd {
  constructor(
    public url: string,
    public name: string,
    public secretKey: string,
    public branch: string
  ) {
  }

  static from(raw: any) {
    return new CodebaseToAdd(
      raw.url,
      raw.name,
      raw.secretKey,
      raw.branch
    );
  }
}
