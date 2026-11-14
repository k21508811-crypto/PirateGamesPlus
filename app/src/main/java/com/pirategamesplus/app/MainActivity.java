package com.pirategamesplus.app;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {
    private final int bg = Color.rgb(12, 12, 15);
    private final int panel = Color.rgb(25, 25, 31);
    private final int red = Color.rgb(232, 45, 55);
    private final int white = Color.rgb(245, 245, 247);
    private final int muted = Color.rgb(165, 165, 175);
    private LinearLayout list;
    private final List<Game> games = new ArrayList<>();

    private static class Game {
        String name, description, platform, category, url;
        Game(String n, String d, String p, String c, String u) {
            name=n; description=d; platform=p; category=c; url=u;
        }
    }

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(bg);
        getWindow().setNavigationBarColor(bg);
        getWindow().getDecorView().setSystemUiVisibility(0);

        games.add(new Game("Mindustry", "Стратегия и строительство заводов. Открытый исходный код.", "Android · ПК", "Стратегии", "https://github.com/Anuken/Mindustry/releases"));
        games.add(new Game("SuperTuxKart", "Бесплатные аркадные гонки с открытым исходным кодом.", "ПК", "Гонки", "https://supertuxkart.net/Download"));
        games.add(new Game("OpenTTD", "Экономическая стратегия о развитии транспортной сети.", "Android · ПК", "Стратегии", "https://www.openttd.org/downloads/openttd-releases/latest.html"));
        games.add(new Game("0 A.D.", "Историческая стратегия в реальном времени.", "ПК", "Стратегии", "https://play0ad.com/download/"));
        games.add(new Game("Luanti", "Открытая песочница с поддержкой пользовательских миров.", "Android · ПК", "Песочницы", "https://www.luanti.org/downloads/"));

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(bg);
        root.setPadding(dp(20), dp(18), dp(20), dp(12));

        TextView brand = text("PIRATE GAMES PLUS", 13, red, true);
        brand.setLetterSpacing(0.12f);
        root.addView(brand);

        TextView title = text("Игры без лишнего шума.", 28, white, true);
        LinearLayout.LayoutParams titleParams = new LinearLayout.LayoutParams(-1, -2);
        titleParams.topMargin = dp(10);
        root.addView(title, titleParams);

        TextView subtitle = text("Каталог бесплатных игр и проектов с открытым исходным кодом.", 14, muted, false);
        LinearLayout.LayoutParams subParams = new LinearLayout.LayoutParams(-1, -2);
        subParams.topMargin = dp(7);
        root.addView(subtitle, subParams);

        EditText search = new EditText(this);
        search.setSingleLine(true);
        search.setTextColor(white);
        search.setHintTextColor(muted);
        search.setHint("Найти игру...");
        search.setPadding(dp(15), 0, dp(15), 0);
        search.setTextSize(15);
        search.setBackground(round(panel, 14));
        LinearLayout.LayoutParams searchParams = new LinearLayout.LayoutParams(-1, dp(52));
        searchParams.topMargin = dp(20);
        root.addView(search, searchParams);

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(false);
        list = new LinearLayout(this);
        list.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams listParams = new LinearLayout.LayoutParams(-1, -2);
        listParams.topMargin = dp(16);
        list.addView(text("ПОДБОРКА ИГР", 12, muted, true));
        scroll.addView(list);
        root.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));

        TextView footer = text("Скачивание происходит на официальных сайтах разработчиков.", 11, muted, false);
        footer.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams footParams = new LinearLayout.LayoutParams(-1, -2);
        footParams.topMargin = dp(10);
        root.addView(footer, footParams);

        setContentView(root);
        render("");
        search.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) { render(s.toString()); }
            public void afterTextChanged(Editable e) {}
        });
    }

    private void render(String query) {
        list.removeAllViews();
        int count = 0;
        String q = query.toLowerCase(Locale.ROOT).trim();
        for (Game game : games) {
            if (!(game.name.toLowerCase(Locale.ROOT).contains(q) ||
                  game.description.toLowerCase(Locale.ROOT).contains(q) ||
                  game.category.toLowerCase(Locale.ROOT).contains(q))) continue;
            count++;
            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.VERTICAL);
            card.setPadding(dp(16), dp(15), dp(16), dp(15));
            card.setBackground(round(panel, 16));
            LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(-1, -2);
            cp.topMargin = dp(10);
            list.addView(card, cp);

            LinearLayout row = new LinearLayout(this);
            row.setGravity(Gravity.CENTER_VERTICAL);
            TextView name = text(game.name, 19, white, true);
            row.addView(name, new LinearLayout.LayoutParams(0, -2, 1));
            TextView tag = text(game.category.toUpperCase(Locale.ROOT), 10, red, true);
            row.addView(tag);
            card.addView(row);

            TextView desc = text(game.description, 13, muted, false);
            LinearLayout.LayoutParams dp = new LinearLayout.LayoutParams(-1, -2);
            dp.topMargin = dp(8);
            card.addView(desc, dp);

            TextView platform = text(game.platform, 12, muted, true);
            LinearLayout.LayoutParams pp = new LinearLayout.LayoutParams(-1, -2);
            pp.topMargin = dp(10);
            card.addView(platform, pp);

            TextView button = text("ОФИЦИАЛЬНАЯ ЗАГРУЗКА  ↗", 12, white, true);
            button.setGravity(Gravity.CENTER);
            button.setPadding(dp(12), dp(12), dp(12), dp(12));
            button.setBackground(round(red, 10));
            LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams(-1, dp(44));
            bp.topMargin = dp(12);
            card.addView(button, bp);
            button.setOnClickListener(v -> {
                try { startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(game.url))); }
                catch (Exception ignored) {}
            });
        }
        if (count == 0) {
            TextView empty = text("Ничего не найдено.", 14, muted, false);
            LinearLayout.LayoutParams ep = new LinearLayout.LayoutParams(-1, -2);
            ep.topMargin = dp(24);
            list.addView(empty, ep);
        }
    }

    private TextView text(String value, int size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return t;
    }

    private GradientDrawable round(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(dp(radius));
        return d;
    }
    private int dp(int value) { return (int)(value * getResources().getDisplayMetrics().density + 0.5f); }
}
