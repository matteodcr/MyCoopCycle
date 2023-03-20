import { IProduit } from 'app/shared/model/produit.model';
import { ICategorieProduit } from 'app/shared/model/categorie-produit.model';

export interface IItem {
  id?: number;
  prix?: number;
  produit?: IProduit | null;
  categories?: ICategorieProduit[] | null;
}

export const defaultValue: Readonly<IItem> = {};
