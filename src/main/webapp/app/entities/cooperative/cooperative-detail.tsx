import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cooperative.reducer';

export const CooperativeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const cooperativeEntity = useAppSelector(state => state.cooperative.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="cooperativeDetailsHeading">
          <Translate contentKey="myCoopCycleApp.cooperative.detail.title">Cooperative</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{cooperativeEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="myCoopCycleApp.cooperative.name">Name</Translate>
            </span>
          </dt>
          <dd>{cooperativeEntity.name}</dd>
          <dt>
            <Translate contentKey="myCoopCycleApp.cooperative.livreurs">Livreurs</Translate>
          </dt>
          <dd>
            {cooperativeEntity.livreurs
              ? cooperativeEntity.livreurs.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {cooperativeEntity.livreurs && i === cooperativeEntity.livreurs.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="myCoopCycleApp.cooperative.commercants">Commercants</Translate>
          </dt>
          <dd>
            {cooperativeEntity.commercants
              ? cooperativeEntity.commercants.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {cooperativeEntity.commercants && i === cooperativeEntity.commercants.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/cooperative" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cooperative/${cooperativeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CooperativeDetail;
