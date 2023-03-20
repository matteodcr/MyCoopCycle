import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Cooperative from './cooperative';
import CooperativeDetail from './cooperative-detail';
import CooperativeUpdate from './cooperative-update';
import CooperativeDeleteDialog from './cooperative-delete-dialog';

const CooperativeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Cooperative />} />
    <Route path="new" element={<CooperativeUpdate />} />
    <Route path=":id">
      <Route index element={<CooperativeDetail />} />
      <Route path="edit" element={<CooperativeUpdate />} />
      <Route path="delete" element={<CooperativeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CooperativeRoutes;
