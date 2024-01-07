export enum FORM_MODE {
  CREATE,
  UPDATE
}

export abstract class AppConstants {
  static readonly DATE_FORMAT: string = "dd-MM-yyyy";
  static readonly DATE_LONG_FORMAT: string = "EEEE d MMMM yyyy";
}
