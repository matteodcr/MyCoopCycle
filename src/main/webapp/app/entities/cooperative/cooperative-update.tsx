import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILivreur } from 'app/shared/model/livreur.model';
import { getEntities as getLivreurs } from 'app/entities/livreur/livreur.reducer';
import { ICommercant } from 'app/shared/model/commercant.model';
import { getEntities as getCommercants } from 'app/entities/commercant/commercant.reducer';
import { ICooperative } from 'app/shared/model/cooperative.model';
import { getEntity, updateEntity, createEntity, reset } from './cooperative.reducer';

export const CooperativeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const livreurs = useAppSelector(state => state.livreur.entities);
  const commercants = useAppSelector(state => state.commercant.entities);
  const cooperativeEntity = useAppSelector(state => state.cooperative.entity);
  const loading = useAppSelector(state => state.cooperative.loading);
  const updating = useAppSelector(state => state.cooperative.updating);
  const updateSuccess = useAppSelector(state => state.cooperative.updateSuccess);

  const handleClose = () => {
    navigate('/cooperative');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLivreurs({}));
    dispatch(getCommercants({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...cooperativeEntity,
      ...values,
      livreurs: mapIdList(values.livreurs),
      commercants: mapIdList(values.commercants),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...cooperativeEntity,
          livreurs: cooperativeEntity?.livreurs?.map(e => e.id.toString()),
          commercants: cooperativeEntity?.commercants?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myCoopCycleApp.cooperative.home.createOrEditLabel" data-cy="CooperativeCreateUpdateHeading">
            <Translate contentKey="myCoopCycleApp.cooperative.home.createOrEditLabel">Create or edit a Cooperative</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="cooperative-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myCoopCycleApp.cooperative.name')}
                id="cooperative-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 3, message: translate('entity.validation.minlength', { min: 3 }) },
                  maxLength: { value: 50, message: translate('entity.validation.maxlength', { max: 50 }) },
                }}
              />
              <ValidatedField
                label={translate('myCoopCycleApp.cooperative.livreurs')}
                id="cooperative-livreurs"
                data-cy="livreurs"
                type="select"
                multiple
                name="livreurs"
              >
                <option value="" key="0" />
                {livreurs
                  ? livreurs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('myCoopCycleApp.cooperative.commercants')}
                id="cooperative-commercants"
                data-cy="commercants"
                type="select"
                multiple
                name="commercants"
              >
                <option value="" key="0" />
                {commercants
                  ? commercants.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cooperative" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CooperativeUpdate;
