import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './commercant.reducer';

export const CommercantDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const commercantEntity = useAppSelector(state => state.commercant.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="commercantDetailsHeading">
          <Translate contentKey="myCoopCycleApp.commercant.detail.title">Commercant</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{commercantEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="myCoopCycleApp.commercant.name">Name</Translate>
            </span>
          </dt>
          <dd>{commercantEntity.name}</dd>
          <dt>
            <span id="location">
              <Translate contentKey="myCoopCycleApp.commercant.location">Location</Translate>
            </span>
          </dt>
          <dd>{commercantEntity.location}</dd>
        </dl>
        <Button tag={Link} to="/commercant" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/commercant/${commercantEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CommercantDetail;
