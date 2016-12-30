INSERT INTO artorias_dev.blog.author(name, email, password, created_on, updated_on, is_active, is_banned)
VALUES('Devin Austin', 'devin.austin@gmail.com', 'fart123', now(), now(), true, false);

INSERT INTO artorias_dev.blog.post(title, slug, body, author_id, created_on, updated_on, published_on)
SELECT 'Test Post 1', 'test-post-1', 'This is a test post.', artorias_dev.blog.author.author_id, now(), now(), now()
FROM artorias_dev.blog.author;

INSERT INTO artorias_dev.blog.post(title, slug, body, author_id, created_on, updated_on, published_on)
SELECT 'Test Post 2', 'test-post-2', 'This is another test post.', artorias_dev.blog.author.author_id, now(), now(), now()
FROM artorias_dev.blog.author;

INSERT INTO artorias_dev.blog.post(title, slug, body, author_id, created_on, updated_on, published_on)
SELECT 'Test Post 3', 'test-post-3', 'This is an unpublished test post.', artorias_dev.blog.author.author_id, now(), now(), null
FROM artorias_dev.blog.author;



