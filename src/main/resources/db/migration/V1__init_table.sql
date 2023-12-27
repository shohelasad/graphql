--create database sentryc_interview schema;

create table producers
(
    id  uuid  not null constraint "producersPK" primary key,
    name   varchar(255),
    created_at timestamp not null
);

create table marketplaces
(
    id  varchar(255) not null constraint "marketplacesPK" primary key,
    description varchar(255)
);

create table seller_infos
(
    id  uuid  not null constraint "seller_infosPK" primary key,
    marketplace_id varchar(255) constraint "FKr8ekbqgwa3g0uhgbaa1tchf09" references marketplaces,
    seller_name varchar(2048) not null,
    url  varchar(2048),
    country varchar(255),
    external_id varchar(255),
    constraint "UK12xaxk0c1mwxr3ovycs1qxtbk" unique (marketplace_id, external_id)
);

create table sellers
(
    id uuid not null constraint "marketplace_sellersPK" primary key,
    producer_id uuid not null constraint "FK6y70nxr3lhubusfq6ub427ien" references producers,
    seller_info_id uuid constraint "FKp2fkfcqcndx9x9xkhk5va3cq4" references seller_infos,
    state varchar(255) default 'BLOCKLISTED'::character varying not null
);

-- Indexing

-- Sellers Table
CREATE INDEX idx_seller_info_id ON sellers (seller_info_id);
CREATE INDEX idx_producer_id ON sellers (producer_id);
CREATE INDEX idx_state ON sellers (state);

-- Seller Infos Table
CREATE INDEX idx_seller_info_name ON seller_infos (seller_name);
CREATE INDEX idx_external_id ON seller_infos (external_id);

-- Marketplace Table
CREATE INDEX idx_marketplace_id ON marketplaces (id);

