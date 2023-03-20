import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './commande.reducer';

export const CommandeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const commandeEntity = useAppSelector(state => state.commande.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="commandeDetailsHeading">
          <Translate contentKey="myCoopCycleApp.commande.detail.title">Commande</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{commandeEntity.id}</dd>
          <dt>
            <span id="dateLivraison">
              <Translate contentKey="myCoopCycleApp.commande.dateLivraison">Date Livraison</Translate>
            </span>
          </dt>
          <dd>
            {commandeEntity.dateLivraison ? <TextFormat value={commandeEntity.dateLivraison} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="myCoopCycleApp.commande.client">Client</Translate>
          </dt>
          <dd>{commandeEntity.client ? commandeEntity.client.id : ''}</dd>
          <dt>
            <Translate contentKey="myCoopCycleApp.commande.livreur">Livreur</Translate>
          </dt>
          <dd>{commandeEntity.livreur ? commandeEntity.livreur.id : ''}</dd>
          <dt>
            <Translate contentKey="myCoopCycleApp.commande.commercant">Commercant</Translate>
          </dt>
          <dd>{commandeEntity.commercant ? commandeEntity.commercant.id : ''}</dd>
          <dt>
            <Translate contentKey="myCoopCycleApp.commande.produit">Produit</Translate>
          </dt>
          <dd>{commandeEntity.produit ? commandeEntity.produit.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/commande" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/commande/${commandeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CommandeDetail;
