-- Seed services (INSERT IGNORE = skip if already exists)
INSERT IGNORE INTO services (name, color, emoji, active) VALUES
  ('Restaurant', '#E07A5F', '🍽️',  1),
  ('Go Kart',    '#3D405B', '🏎️',  1),
  ('Paintball',  '#81B29A', '🎯',  1),
  ('Park Entry', '#F2CC8F', '🎟️', 1);

-- Seed expense categories
INSERT IGNORE INTO expense_categories (name, active) VALUES
  ('Restaurant',  1),
  ('Go Kart',     1),
  ('Paintball',   1),
  ('Park Entry',  1),
  ('Utilities',   1),
  ('Staff',       1),
  ('Maintenance', 1),
  ('Other',       1);

-- Seed users (passwords are bcrypt of "admin123" and "worker123")
INSERT IGNORE INTO users (username, password, full_name, role, active) VALUES
  ('admin',   '$2a$12$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Park Owner',  'OWNER',  1),
  ('worker',  '$2a$12$GkLwbFAUe/iK1B/IcloRHu5GHCBiVH2bS8Y1bHR4c8SGGE4yTLLEy', 'Staff Member','WORKER', 1);
