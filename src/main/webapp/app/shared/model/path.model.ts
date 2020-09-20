export interface IPath {
  id?: number;
  name?: string;
  path?: string;
}

export class Path implements IPath {
  constructor(public id?: number, public name?: string, public path?: string) {}
}
