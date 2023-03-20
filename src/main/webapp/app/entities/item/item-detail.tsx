import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './item.reducer';

export const ItemDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const itemEntity = useAppSelector(state => state.item.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="itemDetailsHeading">
          <Translate contentKey="myCoopCycleApp.item.detail.title">Item</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{itemEntity.id}</dd>
          <dt>
            <span id="prix">
              <Translate contentKey="myCoopCycleApp.item.prix">Prix</Translate>
            </span>
          </dt>
          <dd>{itemEntity.prix}</dd>
          <dt>
            <Translate contentKey="myCoopCycleApp.item.produit">Produit</Translate>
          </dt>
          <dd>{itemEntity.produit ? itemEntity.produit.id : ''}</dd>
          <dt>
            <Translate contentKey="myCoopCycleApp.item.categories">Categories</Translate>
          </dt>
          <dd>
            {itemEntity.categories
              ? itemEntity.categories.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {itemEntity.categories && i === itemEntity.categories.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/item/${itemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ItemDetail;
