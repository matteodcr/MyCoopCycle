import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/commercant">
        <Translate contentKey="global.menu.entities.commercant" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/livreur">
        <Translate contentKey="global.menu.entities.livreur" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/cooperative">
        <Translate contentKey="global.menu.entities.cooperative" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/client">
        <Translate contentKey="global.menu.entities.client" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/produit">
        <Translate contentKey="global.menu.entities.produit" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/categorie-produit">
        <Translate contentKey="global.menu.entities.categorieProduit" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/commande">
        <Translate contentKey="global.menu.entities.commande" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/item">
        <Translate contentKey="global.menu.entities.item" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
