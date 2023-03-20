import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { ILivreur } from 'app/shared/model/livreur.model';
import { getEntities as getLivreurs } from 'app/entities/livreur/livreur.reducer';
import { ICommercant } from 'app/shared/model/commercant.model';
import { getEntities as getCommercants } from 'app/entities/commercant/commercant.reducer';
import { IItem } from 'app/shared/model/item.model';
import { getEntities as getItems } from 'app/entities/item/item.reducer';
import { ICommande } from 'app/shared/model/commande.model';
import { getEntity, updateEntity, createEntity, reset } from './commande.reducer';

export const CommandeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const clients = useAppSelector(state => state.client.entities);
  const livreurs = useAppSelector(state => state.livreur.entities);
  const commercants = useAppSelector(state => state.commercant.entities);
  const items = useAppSelector(state => state.item.entities);
  const commandeEntity = useAppSelector(state => state.commande.entity);
  const loading = useAppSelector(state => state.commande.loading);
  const updating = useAppSelector(state => state.commande.updating);
  const updateSuccess = useAppSelector(state => state.commande.updateSuccess);

  const handleClose = () => {
    navigate('/commande');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getClients({}));
    dispatch(getLivreurs({}));
    dispatch(getCommercants({}));
    dispatch(getItems({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.dateLivraison = convertDateTimeToServer(values.dateLivraison);

    const entity = {
      ...commandeEntity,
      ...values,
      client: clients.find(it => it.id.toString() === values.client.toString()),
      livreur: livreurs.find(it => it.id.toString() === values.livreur.toString()),
      commercant: commercants.find(it => it.id.toString() === values.commercant.toString()),
      produit: items.find(it => it.id.toString() === values.produit.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          dateLivraison: displayDefaultDateTime(),
        }
      : {
          ...commandeEntity,
          dateLivraison: convertDateTimeFromServer(commandeEntity.dateLivraison),
          client: commandeEntity?.client?.id,
          livreur: commandeEntity?.livreur?.id,
          commercant: commandeEntity?.commercant?.id,
          produit: commandeEntity?.produit?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="myCoopCycleApp.commande.home.createOrEditLabel" data-cy="CommandeCreateUpdateHeading">
            <Translate contentKey="myCoopCycleApp.commande.home.createOrEditLabel">Create or edit a Commande</Translate>
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
                  id="commande-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('myCoopCycleApp.commande.dateLivraison')}
                id="commande-dateLivraison"
                name="dateLivraison"
                data-cy="dateLivraison"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="commande-client"
                name="client"
                data-cy="client"
                label={translate('myCoopCycleApp.commande.client')}
                type="select"
              >
                <option value="" key="0" />
                {clients
                  ? clients.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="commande-livreur"
                name="livreur"
                data-cy="livreur"
                label={translate('myCoopCycleApp.commande.livreur')}
                type="select"
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
                id="commande-commercant"
                name="commercant"
                data-cy="commercant"
                label={translate('myCoopCycleApp.commande.commercant')}
                type="select"
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
              <ValidatedField
                id="commande-produit"
                name="produit"
                data-cy="produit"
                label={translate('myCoopCycleApp.commande.produit')}
                type="select"
              >
                <option value="" key="0" />
                {items
                  ? items.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/commande" replace color="info">
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

export default CommandeUpdate;
