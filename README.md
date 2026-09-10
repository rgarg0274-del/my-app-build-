# TickClock

A minimal Android app: black screen, one 100x100dp circle button.

- **Tap once (white → grey):** starts a foreground service that loops your
  `tick_tock.mp3` continuously — including with the app closed/backgrounded,
  screen off, etc.
- **Reopen the app while it's ticking:** the button shows grey (real state,
  read from the running service).
- **Tap again (grey → white):** stops the service and the sound.

## Why this is a project, not a ready .apk

Compiling an Android app requires Google's Android SDK / Gradle plugin,
downloaded from Google's Maven servers. The sandbox this was built in has no
network access to those servers (only a short allow-list: GitHub, PyPI, npm,
crates.io, Ubuntu archives) — so an actual build could not be run here.
Everything else is done: full source, resources, your sound file, and your
exact circle colors (`#FFFFFF` / `#959595` on `#000000`) sampled straight
from the two reference images.

## Get the installable .apk — pick one

### Option A: GitHub Actions (no software install, ~2 minutes)
1. Create a new **public or private** GitHub repo and upload this whole
   folder's contents to it (or `git init && git add . && git commit -m init
   && git push`).
2. GitHub will automatically run `.github/workflows/build.yml` on push.
3. Open the **Actions** tab → the finished run → download the
   `TickClock-debug-apk` artifact. Unzip it to get `app-debug.apk`.
4. Copy it to your phone and install (allow "install unknown apps" for
   whatever app you use to open it).

### Option B: Android Studio (if you already have it)
1. Open this folder as a project.
2. Let it sync, then **Run ▶** on a device/emulator, or
   **Build → Build APK(s)** to get the file directly.

## One real-world caveat

Some phones (Xiaomi/MIUI, Samsung, Oppo, etc.) aggressively kill background
apps to save battery. If ticking stops after a while in the background,
go to the app's battery settings and disable battery optimization /
allow it to run in the background — that's an OS setting, not something
an app can fully override.

## Note on "180×320 resolution"

Modern Android doesn't let an app force the device's actual screen
resolution. This is built at that scale in **dp** (density-independent
pixels) instead: full-screen black background with a 100×100dp circle
dead-centered — the same proportions as your reference mockups — so it
renders correctly on any real phone screen.
