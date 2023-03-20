import { ICooperative } from 'app/shared/model/cooperative.model';

export interface ILivreur {
  id?: number;
  name?: string;
  cooperatives?: ICooperative[] | null;
}

export const defaultValue: Readonly<ILivreur> = {};
