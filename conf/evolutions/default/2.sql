# --- !Ups
CREATE TABLE `comments` (
  `id` integer NOT NULL AUTO_INCREMENT,
  `body` TEXT NOT NULL,
  `created_at` timestamp NOT NULL,
  `thread_id` integer NOT NULL,
  PRIMARY KEY(id),
  INDEX thread_id_idx(thread_id)
);

# --- !Downs
DROP TABLE `comments`;
