import { IItem } from 'app/shared/model/item.model';

export interface ICategorieProduit {
  id?: number;
  name?: string;
  items?: IItem[] | null;
}

export const defaultValue: Readonly<ICategorieProduit> = {};
