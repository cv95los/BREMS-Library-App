import {Resource} from './resource.model';

export interface ResourceCopy {
  id?: number;
  locationCode: string;
  resource: Resource;
}
