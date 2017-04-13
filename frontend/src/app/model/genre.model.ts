import {Resource} from './resource.model';

export interface Genre {
  id?: number;
  name: string;
  resources: Resource[];
}
