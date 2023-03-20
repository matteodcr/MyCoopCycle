import commercant from 'app/entities/commercant/commercant.reducer';
import livreur from 'app/entities/livreur/livreur.reducer';
import cooperative from 'app/entities/cooperative/cooperative.reducer';
import client from 'app/entities/client/client.reducer';
import produit from 'app/entities/produit/produit.reducer';
import categorieProduit from 'app/entities/categorie-produit/categorie-produit.reducer';
import commande from 'app/entities/commande/commande.reducer';
import item from 'app/entities/item/item.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  commercant,
  livreur,
  cooperative,
  client,
  produit,
  categorieProduit,
  commande,
  item,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
