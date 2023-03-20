import { ICooperative } from 'app/shared/model/cooperative.model';

export interface ICommercant {
  id?: number;
  name?: string;
  location?: string | null;
  cooperatives?: ICooperative[] | null;
}

export const defaultValue: Readonly<ICommercant> = {};
