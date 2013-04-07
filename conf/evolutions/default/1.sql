# --- !Ups
CREATE TABLE `threads` (
  `id` integer NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `createdAt` timestamp NOT NULL,
  PRIMARY KEY(id)
);

# --- !Downs
DROP TABLE `threads`;
