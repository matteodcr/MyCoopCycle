import { ILivreur } from 'app/shared/model/livreur.model';
import { ICommercant } from 'app/shared/model/commercant.model';

export interface ICooperative {
  id?: number;
  name?: string;
  livreurs?: ILivreur[] | null;
  commercants?: ICommercant[] | null;
}

export const defaultValue: Readonly<ICooperative> = {};
