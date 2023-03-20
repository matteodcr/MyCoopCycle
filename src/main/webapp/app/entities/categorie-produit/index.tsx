import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CategorieProduit from './categorie-produit';
import CategorieProduitDetail from './categorie-produit-detail';
import CategorieProduitUpdate from './categorie-produit-update';
import CategorieProduitDeleteDialog from './categorie-produit-delete-dialog';

const CategorieProduitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CategorieProduit />} />
    <Route path="new" element={<CategorieProduitUpdate />} />
    <Route path=":id">
      <Route index element={<CategorieProduitDetail />} />
      <Route path="edit" element={<CategorieProduitUpdate />} />
      <Route path="delete" element={<CategorieProduitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CategorieProduitRoutes;
