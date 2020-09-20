export interface IExtension {
  id?: number;
  name?: string;
}

export class Extension implements IExtension {
  constructor(public id?: number, public name?: string) {}
}
