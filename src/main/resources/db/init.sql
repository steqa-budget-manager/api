create table users
(
    id         bigint primary key generated always as identity,
    email      varchar(255) not null unique,
    password   varchar(255) not null,
    created_at timestamp(6) with time zone not null default now()
);

create table accounts
(
    id      bigint primary key generated always as identity,
    user_id bigint       not null,
    constraint FK_accounts_users
        foreign key (user_id) references users (id) on delete cascade,
    name    varchar(255) not null,
    visible bool         not null default true,
    created_at timestamp(6) with time zone not null default now()
);

create type transactiontype as enum ('INCOME', 'EXPENSE');

create table transaction_categories
(
    id      bigint primary key generated always as identity,
    user_id bigint          not null,
    constraint FK_transaction_categories_users
        foreign key (user_id) references users (id) on delete cascade,
    type    transactiontype not null,
    name    varchar(255)    not null,
    visible bool            not null default true,
    created_at timestamp(6) with time zone not null default now()
);

create table transactions
(
    id          bigint primary key generated always as identity,
    user_id     bigint                      not null,
    constraint FK_transactions_users
        foreign key (user_id) references users (id) on delete cascade,
    account_id  bigint                      not null,
    constraint FK_transactions_accounts
        foreign key (account_id) references accounts (id) on delete no action,
    category_id bigint                      not null,
    constraint FK_transactions_transaction_categories
        foreign key (category_id) references transaction_categories (id) on delete no action,
    type        transactiontype             not null,
    amount      bigint                      not null,
    constraint transactions_amount_check check (amount <= 100000000000000000),
    description varchar(1000),
    date        timestamp(6) with time zone not null
);

create table transaction_templates
(
    id          bigint primary key generated always as identity,
    user_id     bigint          not null,
    constraint FK_transaction_templates_users
        foreign key (user_id) references users (id) on delete cascade,
    account_id  bigint          not null,
    constraint FK_transaction_templates_accounts
        foreign key (account_id) references accounts (id) on delete no action,
    category_id bigint          not null,
    constraint FK_transaction_templates_transaction_categories
        foreign key (category_id) references transaction_categories (id) on delete no action,
    type        transactiontype not null,
    constraint transaction_templates_amount_check check (amount <= 100000000000000000),
    amount      bigint          not null,
    description varchar(1000),
    created_at timestamp(6) with time zone not null default now()
);

create table transfers
(
    id              bigint primary key generated always as identity,
    user_id         bigint                      not null,
    constraint FK_transfers_users
        foreign key (user_id) references users (id) on delete cascade,
    from_account_id bigint                      not null,
    constraint FK_transfers_accounts_from
        foreign key (from_account_id) references accounts (id) on delete no action,
    to_account_id   bigint                      not null,
    constraint FK_transfers_accounts_to
        foreign key (to_account_id) references accounts (id) on delete no action,
    amount          bigint                      not null,
    constraint transfers_amount_check check (amount <= 100000000000000000),
    description     varchar(1000),
    date            timestamp(6) with time zone not null
);

create table transaction_regulars
(
    id                 bigint primary key generated always as identity,
    user_id            bigint          not null,
    constraint FK_transaction_templates_users
        foreign key (user_id) references users (id) on delete cascade,
    account_id         bigint          not null,
    constraint FK_transaction_templates_accounts
        foreign key (account_id) references accounts (id) on delete no action,
    category_id        bigint          not null,
    constraint FK_transaction_templates_transaction_categories
        foreign key (category_id) references transaction_categories (id) on delete no action,
    type               transactiontype not null,
    short_name               varchar(255)    not null,
    amount             bigint          not null,
    constraint transaction_regulars_amount_check check (amount <= 100000000000000000),
    description        varchar(1000),
    repetition_rule_id varchar(255),
    created_at timestamp(6) with time zone not null default now()
);