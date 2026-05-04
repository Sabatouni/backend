import { createClient } from "@supabase/supabase-js";
import cors from "cors";
import dotenv from "dotenv";
import express from "express";

dotenv.config();

const app = express();

app.use(cors({
  origin: "*",
  methods: ["GET", "POST", "PATCH", "DELETE"],
  allowedHeaders: ["Content-Type", "Authorization"]
}));

app.use(express.json());

// ✅ Health check (IMPORTANT)
app.get("/", (req, res) => {
  res.send("Backend is running 🚀");
});

// 🔐 Supabase Admin Client
const supabase = createClient(
  process.env.SUPABASE_URL,
  process.env.SUPABASE_SERVICE_ROLE_KEY
);

// 🔒 AUTH MIDDLEWARE
async function requireAdmin(req, res, next) {
  try {
    const authHeader = req.headers.authorization;

    if (!authHeader?.startsWith("Bearer ")) {
      return res.status(401).json({ error: "Missing token" });
    }

    const token = authHeader.split(" ")[1];
    const { data, error } = await supabase.auth.getUser(token);

    if (error || !data?.user) {
      return res.status(401).json({ error: "Invalid token" });
    }

    const role = (data.user.user_metadata?.role || "").toLowerCase();

    if (!["admin", "owner"].includes(role)) {
      return res.status(403).json({ error: "Not authorized" });
    }

    req.user = data.user;
    next();

  } catch (err) {
    return res.status(500).json({ error: err.message || "Server error" });
  }
}

// ── ROUTES ─────────────────────────────

app.get("/admin/users", requireAdmin, async (req, res) => {
  const { data, error } = await supabase.auth.admin.listUsers();
  if (error) return res.status(400).json({ error: error.message });
  res.json(data.users);
});

app.post("/admin/users", requireAdmin, async (req, res) => {
  const { email, password, role = "WORKER", name } = req.body;

  if (!email || !password) {
    return res.status(400).json({ error: "Email & password required" });
  }

  const { data, error } = await supabase.auth.admin.createUser({
    email,
    password,
    email_confirm: true,
    user_metadata: {
      role: role.toUpperCase(),
      name: name || email.split("@")[0],
    },
  });

  if (error) return res.status(400).json({ error: error.message });
  res.json(data.user);
});

app.patch("/admin/role", requireAdmin, async (req, res) => {
  const { userId, role } = req.body;

  const { data, error } = await supabase.auth.admin.updateUserById(userId, {
    user_metadata: { role: role.toUpperCase() },
  });

  if (error) return res.status(400).json({ error: error.message });
  res.json(data);
});

app.patch("/admin/active", requireAdmin, async (req, res) => {
  const { userId, active } = req.body;

  const { data, error } = await supabase.auth.admin.updateUserById(userId, {
    ban_duration: active ? "none" : "876000h",
  });

  if (error) return res.status(400).json({ error: error.message });
  res.json(data);
});

app.post("/admin/reset-password", requireAdmin, async (req, res) => {
  const { email } = req.body;

  const { error } = await supabase.auth.resetPasswordForEmail(email);
  if (error) return res.status(400).json({ error: error.message });

  res.json({ message: "Reset email sent" });
});

app.delete("/admin/users/:id", requireAdmin, async (req, res) => {
  const { id } = req.params;

  const { error } = await supabase.auth.admin.deleteUser(id);
  if (error) return res.status(400).json({ error: error.message });

  res.json({ message: "User deleted" });
});

// 🚀 START SERVER
const PORT = process.env.PORT || 3001;

app.listen(PORT, () => {
  console.log(`🚀 Admin backend running on port ${PORT}`);
});