INSERT INTO public.tags (id, tag_name)
VALUES (1, 'tagName1');

INSERT INTO tags (id, tag_name)
VALUES (2, 'tagName3');

INSERT INTO tags (id, tag_name)
VALUES (3, 'tagName5');

INSERT INTO tags (id, tag_name)
VALUES (4, 'tagName4');

INSERT INTO tags (id, tag_name)
VALUES (5, 'tagName2');

/* --------------filling in the table "gift_certificates"---------------------*/
INSERT INTO gift_certificates (id, name, description, price, duration, create_date, last_update_date)
VALUES (1, 'giftCertificate1', 'description1', 10.1, 1, '2012-12-12', '2012-12-12');

INSERT INTO gift_certificates (id, name, description, price, duration, create_date, last_update_date)
VALUES (2, 'giftCertificate3', 'description3', 30.3, 3, '2012-12-12', '2012-12-12');

INSERT INTO gift_certificates (id, name, description, price, duration, create_date, last_update_date)
VALUES (3, 'giftCertificate2', 'description2', 20.2, 2, '2012-12-12', '2012-12-12');

/* --------------filling in the table "gift_certificates_tags"---------------------*/

INSERT INTO gift_certificates_tags (gift_certificate_id, tag_id)
VALUES (1, 2);

INSERT INTO gift_certificates_tags (gift_certificate_id, tag_id)
VALUES (2, 2);