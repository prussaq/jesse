insert into buy (date, ticket, price, amount) values ('2021-01-21', 'AFLT', 74.32, 80);
insert into buy (date, ticket, price, amount) values ('2021-06-14', 'AFLT', 72.4, 130);
insert into buy (date, ticket, price, amount) values ('2021-09-13', 'AFLT', 67.4, 60);
insert into buy (date, ticket, price, amount) values ('2022-08-08', 'AFLT', 26.64, 40);
insert into buy (date, ticket, price, amount) values ('2022-01-10', 'VTBR', 0.04884, 400000);
insert into buy (date, ticket, price, amount) values ('2022-01-12', 'VTBR', 0.050385, 300000);
insert into buy (date, ticket, price, amount) values ('2022-05-19', 'VTBR', 0.019125, 80000);
insert into buy (date, ticket, price, amount) values ('2022-08-08', 'VTBR', 0.017755, 200000);
insert into buy (date, ticket, price, amount) values ('2021-02-15', 'UWGN', 141.6, 29);
insert into buy (date, ticket, price, amount) values ('2022-01-12', 'UWGN', 81.5, 47);
insert into buy (date, ticket, price, amount) values ('2022-06-20', 'UWGN', 47.9, 220);
insert into buy (date, ticket, price, amount) values ('2022-09-22', 'UWGN', 48.9, 20);
insert into buy (date, ticket, price, amount) values ('2022-10-06', 'UWGN', 37.5, 10);
insert into buy (date, ticket, price, amount) values ('2019-01-01', 'UCSS', 1370, 2);
insert into buy (date, ticket, price, amount) values ('2020-01-20', 'UCSS', 1490, 2);
insert into buy (date, ticket, price, amount) values ('2020-05-20', 'UCSS', 1480, 3);
insert into buy (date, ticket, price, amount) values ('2021-09-08', 'UCSS', 1000, 3);
insert into buy (date, ticket, price, amount) values ('2022-01-11', 'UCSS', 935, 1);
insert into buy (date, ticket, price, amount) values ('2022-01-12', 'UCSS', 890, 7);
insert into buy (date, ticket, price, amount) values ('2022-05-23', 'UCSS', 825, 2);
insert into buy (date, ticket, price, amount) values ('2022-09-22', 'UCSS', 875, 1);
insert into buy (date, ticket, price, amount) values ('2022-05-19', 'SBER', 125.2, 30);
insert into buy (date, ticket, price, amount) values ('2022-06-03', 'SBER', 119.9, 10);
insert into buy (date, ticket, price, amount) values ('2022-06-08', 'SBER', 119.5, 10);

insert into sell (buy_id, date, ticket, price, amount) values (24, '2022-07-14', 'SBER', 136, 10);
insert into sell (buy_id, date, ticket, price, amount) values (11, '2022-07-14', 'UWGN', 59, 20);
insert into sell (buy_id, date, ticket, price, amount) values (19, '2022-05-20', 'UCSS', 1140, 4);

insert into equity (ticket, name) values ('MOEX', 'МосБиржа');
