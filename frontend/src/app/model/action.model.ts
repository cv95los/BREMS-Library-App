import {ResourceCopy} from './resource-copy.model';

export interface Action {
  id?: number;
  dateLoanInit: any;
  dateLoanGiven: any;
  dateLoanReturn: any;
  copy: ResourceCopy;
}
