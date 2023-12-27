-- Insert data into producers table
INSERT INTO producers (id, name, created_at)
VALUES
    ('e7cd8d65-9556-4f94-83db-0d12a1e4c0f0', 'Nike', CURRENT_TIMESTAMP),
    ('14f66f10-4a30-4fe4-9a18-c3de1f6b63a3', 'Adidas', CURRENT_TIMESTAMP);

-- Insert data into marketplaces table
INSERT INTO marketplaces (id, description)
VALUES
    ('amazon.us', 'North American market'),
    ('simply4you.eu', 'European market');

-- Insert data into seller_infos table
INSERT INTO seller_infos (id, marketplace_id, seller_name, url, country, external_id)
VALUES
    ('c0379b89-4604-4970-854b-4646d1e7f384', 'amazon.us', 'Amazon Us', 'https://amazon.com/seller1', 'USA', 'external_id_1'),
    ('ee2f99b1-0b56-4b03-8644-0714b0bc82b5', 'simply4you.eu', 'Simply4you', 'https://simply4you.com/seller2', 'Germany', 'external_id_2');

-- Insert data into sellers table
INSERT INTO sellers (id, producer_id, seller_info_id, state)
VALUES
    ('f46f37b6-0f6b-429e-87b6-39d3c1a331f5', 'e7cd8d65-9556-4f94-83db-0d12a1e4c0f0', 'c0379b89-4604-4970-854b-4646d1e7f384', 'GREYLIST'),
    ('d7a8a580-0056-45c0-9d3c-9477ed6e83f3', '14f66f10-4a30-4fe4-9a18-c3de1f6b63a3', 'ee2f99b1-0b56-4b03-8644-0714b0bc82b5', 'GREYLIST');
