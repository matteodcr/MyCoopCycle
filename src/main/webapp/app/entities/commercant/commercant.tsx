import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICommercant } from 'app/shared/model/commercant.model';
import { getEntities } from './commercant.reducer';

export const Commercant = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const commercantList = useAppSelector(state => state.commercant.entities);
  const loading = useAppSelector(state => state.commercant.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="commercant-heading" data-cy="CommercantHeading">
        <Translate contentKey="myCoopCycleApp.commercant.home.title">Commercants</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="myCoopCycleApp.commercant.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/commercant/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="myCoopCycleApp.commercant.home.createLabel">Create new Commercant</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {commercantList && commercantList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="myCoopCycleApp.commercant.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="myCoopCycleApp.commercant.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="myCoopCycleApp.commercant.location">Location</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {commercantList.map((commercant, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/commercant/${commercant.id}`} color="link" size="sm">
                      {commercant.id}
                    </Button>
                  </td>
                  <td>{commercant.name}</td>
                  <td>{commercant.location}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/commercant/${commercant.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/commercant/${commercant.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/commercant/${commercant.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="myCoopCycleApp.commercant.home.notFound">No Commercants found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Commercant;
