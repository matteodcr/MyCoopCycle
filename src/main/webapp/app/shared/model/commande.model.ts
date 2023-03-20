import dayjs from 'dayjs';
import { IClient } from 'app/shared/model/client.model';
import { ILivreur } from 'app/shared/model/livreur.model';
import { ICommercant } from 'app/shared/model/commercant.model';
import { IItem } from 'app/shared/model/item.model';

export interface ICommande {
  id?: number;
  dateLivraison?: string;
  client?: IClient | null;
  livreur?: ILivreur | null;
  commercant?: ICommercant | null;
  produit?: IItem | null;
}

export const defaultValue: Readonly<ICommande> = {};
