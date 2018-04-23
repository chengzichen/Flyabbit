package com.dhc.library.utils.string;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

import com.dhc.library.utils.AppContext;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 创建者：邓浩宸
 * 时间 ：2017/6/21 20:11
 * 描述 ：TODO 请描述该类职责
 */
@SuppressWarnings(value = {"unused", "WeakerAccess"})
public class SpannableUtils {


    private static Builder builder;

    private SpannableUtils() {
    }


    public static Builder getBuilder(CharSequence charSequence) {
        builder = new Builder(charSequence);
        return builder;
    }


    public static class Builder {
        private int fontSize;
        private boolean fontSizeIsDp;
        private SpannableString spannableString;
        private Index index;

        private int flag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
        private int defaultValue = 0x12000000;

        private int foregroundColor;
        private int backgroundColor;
        private int quoteColor;
        private int first;
        private int rest;
        private boolean isLeadingMargin;
        private int gapWidth;
        private int bulletColor;
        private boolean isBullet;
        private float proportion;
        private float xProportion;
        private boolean isStrikethrough;
        private boolean isUnderline;
        private boolean isSuperscript;
        private boolean isSubscript;
        private boolean isBold;
        private boolean isItalic;
        private boolean isBoldItalic;
        private String fontFamily;
        private Layout.Alignment align;
        private Bitmap bitmap;
        private boolean imageIsBitmap;
        private Drawable drawable;
        private boolean imageIsDrawable;
        private Uri uri;
        private boolean imageIsUri;
        private int resourceId;
        private boolean imageIsResourceId;
        private ClickableSpan clickSpan;
        private String url;
        private float radius;
        private BlurMaskFilter.Blur style;
        private boolean isBlur;

        private Builder(CharSequence charSequence) {
            spannableString = new SpannableString(charSequence);

            foregroundColor = defaultValue;
            backgroundColor = defaultValue;
            quoteColor = defaultValue;
            proportion = -1;
            xProportion = -1;
        }


        /**
         * 设置前景色
         *
         * @param color 前景色
         * @return {@link Index}
         */
        public Index setForegroundColor(@ColorInt int color) {
            this.foregroundColor = color;
            return getIndex();
        }


        /**
         * 设置背景色
         *
         * @param color 背景色
         * @return {@link Index}
         */
        public Index setBackgroundColor(@ColorInt int color) {
            this.backgroundColor = color;
            return getIndex();
        }


        /**
         * 设置字体尺寸
         *
         * @param size 尺寸
         * @return {@link Builder}
         */
        public Index setFontSize(int size) {
            this.fontSize = size;
            this.fontSizeIsDp = false;
            return getIndex();
        }

        /**
         * 设置字体尺寸
         *
         * @param size 尺寸
         * @param isDp 是否使用dip
         * @return {@link Builder}
         */
        public Index setFontSize(int size, boolean isDp) {
            this.fontSize = size;
            this.fontSizeIsDp = isDp;
            return getIndex();
        }


        /**
         * 设置引用线的颜色
         *
         * @param color 引用线的颜色
         * @return {@link Index}
         */
        public Index setQuoteColor(@ColorInt int color) {
            this.quoteColor = color;
            return getIndex();
        }

        /**
         * 设置缩进
         *
         * @param first 首行缩进
         * @param rest  剩余行缩进
         * @return {@link Index}
         */
        public Index setLeadingMargin(int first, int rest) {
            this.first = first;
            this.rest = rest;
            isLeadingMargin = true;
            return getIndex();
        }

        /**
         * 设置列表标记
         *
         * @param gapWidth 列表标记和文字间距离
         * @param color    列表标记的颜色
         * @return {@link Index}
         */
        public Index setBullet(int gapWidth, int color) {
            this.gapWidth = gapWidth;
            bulletColor = color;
            isBullet = true;
            return getIndex();
        }

        /**
         * 设置字体比例
         *
         * @param proportion 比例
         * @return {@link Index}
         */
        public Index setProportion(float proportion) {
            this.proportion = proportion;
            return getIndex();
        }

        /**
         * 设置字体横向比例
         *
         * @param proportion 比例
         * @return {@link Index}
         */
        public Index setXProportion(float proportion) {
            this.xProportion = proportion;
            return getIndex();
        }

        /**
         * 设置删除线
         *
         * @return {@link Index}
         */
        public Index setStrikethrough() {
            this.isStrikethrough = true;
            return getIndex();
        }

        /**
         * 设置下划线
         *
         * @return {@link Index}
         */
        public Index setUnderline() {
            this.isUnderline = true;
            return getIndex();
        }

        /**
         * 设置上标
         *
         * @return {@link Index}
         */
        public Index setSuperscript() {
            this.isSuperscript = true;
            return getIndex();
        }

        /**
         * 设置下标
         *
         * @return {@link Index}
         */
        public Index setSubscript() {
            this.isSubscript = true;
            return getIndex();
        }

        /**
         * 设置粗体
         *
         * @return {@link Index}
         */
        public Index setBold() {
            isBold = true;
            return getIndex();
        }

        /**
         * 设置斜体
         *
         * @return {@link Index}
         */
        public Index setItalic() {
            isItalic = true;
            return getIndex();
        }

        /**
         * 设置粗斜体
         *
         * @return {@link Index}
         */
        public Index setBoldItalic() {
            isBoldItalic = true;
            return getIndex();
        }

        /**
         * 设置字体
         *
         * @param fontFamily 字体
         *                   <ul>
         *                   <li>monospace</li>
         *                   <li>serif</li>
         *                   <li>sans-serif</li>
         *                   </ul>
         * @return {@link Index}
         */
        public Index setFontFamily(@Nullable String fontFamily) {
            this.fontFamily = fontFamily;
            return getIndex();
        }

        /**
         * 设置对齐
         *
         * @param align 对其方式
         *              <ul>
         *              <li>{@link Layout.Alignment#ALIGN_NORMAL}正常</li>
         *              <li>{@link Layout.Alignment#ALIGN_OPPOSITE}相反</li>
         *              <li>{@link Layout.Alignment#ALIGN_CENTER}居中</li>
         *              </ul>
         * @return {@link Index}
         */
        public Index setAlign(@Nullable Layout.Alignment align) {
            this.align = align;
            return getIndex();
        }

        /**
         * 设置图片
         *
         * @param bitmap 图片位图
         * @return {@link Index}
         */
        public Index setBitmap(@NonNull Bitmap bitmap) {
            this.bitmap = bitmap;
            imageIsBitmap = true;
            return getIndex();
        }

        /**
         * 设置图片
         *
         * @param drawable 图片资源
         * @return {@link Index}
         */
        public Index setDrawable(@NonNull Drawable drawable) {
            this.drawable = drawable;
            imageIsDrawable = true;
            return getIndex();
        }

        /**
         * 设置图片
         *
         * @param uri 图片uri
         * @return {@link Index}
         */
        public Index setUri(@NonNull Uri uri) {
            this.uri = uri;
            imageIsUri = true;
            return getIndex();
        }

        /**
         * 设置图片
         *
         * @param resourceId 图片资源id
         * @return {@link Index}
         */
        public Index setResourceId(@DrawableRes int resourceId) {
            this.resourceId = resourceId;
            imageIsResourceId = true;
            return getIndex();
        }

        /**
         * 设置点击事件
         * <p>需添加view.setMovementMethod(LinkMovementMethod.getInstance())</p>
         *
         * @param clickSpan 点击事件
         * @return {@link Index}
         */
        public Index setClickSpan(@NonNull ClickableSpan clickSpan) {
            this.clickSpan = clickSpan;
            return getIndex();
        }

        /**
         * 设置超链接
         * <p>需添加view.setMovementMethod(LinkMovementMethod.getInstance())</p>
         *
         * @param url 超链接
         * @return {@link Index}
         */
        public Index setUrl(@NonNull String url) {
            this.url = url;
            return getIndex();
        }

        /**
         * 设置模糊
         * <p>尚存bug，其他地方存在相同的字体的话，相同字体出现在之前的话那么就不会模糊，出现在之后的话那会一起模糊</p>
         * <p>推荐还是把所有字体都模糊这样使用</p>
         *
         * @param radius 模糊半径（需大于0）
         * @param style  模糊样式<ul>
         *               <li>{@link BlurMaskFilter.Blur#NORMAL}</li>
         *               <li>{@link BlurMaskFilter.Blur#SOLID}</li>
         *               <li>{@link BlurMaskFilter.Blur#OUTER}</li>
         *               <li>{@link BlurMaskFilter.Blur#INNER}</li>
         *               </ul>
         * @return {@link Index}
         */
        public Index setBlur(float radius, BlurMaskFilter.Blur style) {
            this.radius = radius;
            this.style = style;
            this.isBlur = true;
            return getIndex();
        }


        /**
         * 创建样式字符串
         *
         * @return 样式字符串
         */
        public SpannableString build() {
            return spannableString;
        }

        @NonNull
        private Index getIndex() {
            if (index == null) {
                index = new Index(spannableString.toString());
            }
            return index;
        }


        private void setSpan() {
            if (fontSize != -1) {
                for (Map.Entry<Integer, Integer> entry : index.getIndex())
                spannableString.setSpan(new AbsoluteSizeSpan(fontSize, fontSizeIsDp), entry.getKey(), entry.getValue(), flag);
                fontSize = -1;
                fontSizeIsDp = false;
            }
            if (foregroundColor != defaultValue) {
                for (Map.Entry<Integer, Integer> entry : index.getIndex())
                    spannableString.setSpan(new ForegroundColorSpan(foregroundColor), entry.getKey(), entry.getValue(), flag);
                foregroundColor = defaultValue;
            }
            if (backgroundColor != defaultValue) {
                for (Map.Entry<Integer, Integer> entry : index.getIndex())
                    spannableString.setSpan(new BackgroundColorSpan(backgroundColor), entry.getKey(), entry.getValue(), flag);
                backgroundColor = defaultValue;
            }
            if (isLeadingMargin) {
                for (Map.Entry<Integer, Integer> entry : index.getIndex())
                    spannableString.setSpan(new LeadingMarginSpan.Standard(first, rest), entry.getKey(), entry.getValue(), flag);
                isLeadingMargin = false;
            }
            if (quoteColor != defaultValue) {
                for (Map.Entry<Integer, Integer> entry : index.getIndex())
                    spannableString.setSpan(new QuoteSpan(quoteColor), entry.getKey(), entry.getValue(), 0);
                quoteColor = defaultValue;
            }
            if (isBullet) {
                for (Map.Entry<Integer, Integer> entry : index.getIndex())
                    spannableString.setSpan(new BulletSpan(gapWidth, bulletColor), entry.getKey(), entry.getValue(), 0);
                isBullet = false;
            }
            if (proportion != -1) {
                for (Map.Entry<Integer, Integer> entry : index.getIndex())
                    spannableString.setSpan(new RelativeSizeSpan(proportion), entry.getKey(), entry.getValue(), flag);
                proportion = -1;
            }
            if (xProportion != -1) {
                for (Map.Entry<Integer, Integer> entry : index.getIndex())
                    spannableString.setSpan(new ScaleXSpan(xProportion), entry.getKey(), entry.getValue(), flag);
                xProportion = -1;
            }
            if (isStrikethrough) {
                for (Map.Entry<Integer, Integer> entry : index.getIndex())
                    spannableString.setSpan(new StrikethroughSpan(), entry.getKey(), entry.getValue(), flag);
                isStrikethrough = false;
            }
            if (isUnderline) {
                for (Map.Entry<Integer, Integer> entry : index.getIndex())
                    spannableString.setSpan(new UnderlineSpan(), entry.getKey(), entry.getValue(), flag);
                isUnderline = false;
            }
            if (isSuperscript) {
                for (Map.Entry<Integer, Integer> entry : index.getIndex())
                    spannableString.setSpan(new SuperscriptSpan(), entry.getKey(), entry.getValue(), flag);
                isSuperscript = false;
            }
            if (isSubscript) {
                for (Map.Entry<Integer, Integer> entry : index.getIndex())
                    spannableString.setSpan(new SubscriptSpan(), entry.getKey(), entry.getValue(), flag);
                isSubscript = false;
            }
            if (isBold) {
                for (Map.Entry<Integer, Integer> entry : index.getIndex())
                    spannableString.setSpan(new StyleSpan(Typeface.BOLD), entry.getKey(), entry.getValue(), flag);
                isBold = false;
            }
            if (isItalic) {
                for (Map.Entry<Integer, Integer> entry : index.getIndex())
                    spannableString.setSpan(new StyleSpan(Typeface.ITALIC), entry.getKey(), entry.getValue(), flag);
                isItalic = false;
            }
            if (isBoldItalic) {
                for (Map.Entry<Integer, Integer> entry : index.getIndex())
                    spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), entry.getKey(), entry.getValue(), flag);
                isBoldItalic = false;
            }
            if (fontFamily != null) {
                for (Map.Entry<Integer, Integer> entry : index.getIndex())
                    spannableString.setSpan(new TypefaceSpan(fontFamily), entry.getKey(), entry.getValue(), flag);
                fontFamily = null;
            }
            if (align != null) {
                for (Map.Entry<Integer, Integer> entry : index.getIndex())
                    spannableString.setSpan(new AlignmentSpan.Standard(align), entry.getKey(), entry.getValue(), flag);
                align = null;
            }
            if (imageIsBitmap || imageIsDrawable || imageIsUri || imageIsResourceId) {
                if (imageIsBitmap) {
                    for (Map.Entry<Integer, Integer> entry : index.getIndex())
                        spannableString.setSpan(new ImageSpan(AppContext.get(), bitmap), entry.getKey(), entry.getValue(), flag);
                    bitmap = null;
                    imageIsBitmap = false;
                } else if (imageIsDrawable) {
                    for (Map.Entry<Integer, Integer> entry : index.getIndex())
                        spannableString.setSpan(new ImageSpan(drawable), entry.getKey(), entry.getValue(), flag);
                    drawable = null;
                    imageIsDrawable = false;
                } else if (imageIsUri) {
                    for (Map.Entry<Integer, Integer> entry : index.getIndex())
                        spannableString.setSpan(new ImageSpan(AppContext.get(), uri), entry.getKey(), entry.getValue(), flag);
                    uri = null;
                    imageIsUri = false;
                } else {
                    for (Map.Entry<Integer, Integer> entry : index.getIndex())
                        spannableString.setSpan(new ImageSpan(AppContext.get(), resourceId), entry.getKey(), entry.getValue(), flag);
                    resourceId = 0;
                    imageIsResourceId = false;
                }
            }
            if (clickSpan != null) {
                for (Map.Entry<Integer, Integer> entry : index.getIndex())
                    spannableString.setSpan(clickSpan, entry.getKey(), entry.getValue(), flag);
                clickSpan = null;
            }
            if (url != null) {
                for (Map.Entry<Integer, Integer> entry : index.getIndex())
                    spannableString.setSpan(new URLSpan(url), entry.getKey(), entry.getValue(), flag);
                url = null;
            }
            if (isBlur) {
                for (Map.Entry<Integer, Integer> entry : index.getIndex())
                    spannableString.setSpan(new MaskFilterSpan(new BlurMaskFilter(radius, style)), entry.getKey(), entry.getValue(), flag);
                isBlur = false;
            }
        }


        public static class Index {

            private int start;
            private int end;
            private int position;
            private final String source;
            private TreeMap<Integer, Integer> map;


            public Index(String s) {
                source = s;
            }

            public Index setContain(String s) {
                int indexOf = source.indexOf(s);
                setIndex(indexOf, indexOf + s.length());
                return this;
            }

            public Index setContain(String s, int start) {
                int indexOf = source.indexOf(s, start);
                setIndex(indexOf, indexOf + s.length());
                return this;
            }


            public Index setRegex(String regex) {
                Matcher m = Pattern.compile(regex).matcher(source);

                while (m.find()) {
                    setContain(m.group(), position);
                }

                return this;
            }


            public Index setStart(int start) {
                if (start > this.start) {
                    this.start = start;
                }
                if (this.start > this.end) {
                    this.end = this.start;
                }
                return this;
            }

            public Index setStart(String c) {
                setStart(source.indexOf(c));
                return this;
            }

            public Index setEnd(String c) {
                setEnd(source.lastIndexOf(c));
                return this;
            }

            public Index setEnd(int end) {
                if (end <= source.length()) {
                    this.end = end;
                }
                return this;
            }

            public Index setMaxEnd() {
                end = source.length();
                return this;
            }

            public Index setIndex(int start, int end) {
                boolean canPut = start >= 0 && start <= end;
                if (map == null) {
                    map = new TreeMap<>();
                }

                for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                    canPut = canPut && entry.getKey() < start && start < entry.getValue();
                }

                if (canPut) {
                    map.put(start, end);
                    position = end;
                }
                return this;
            }


            public Builder create() {
                if (map == null) {
                    setIndex(start, end);
                }
                builder.setSpan();
                builder.index = null;
                return builder;
            }


            private Set<Map.Entry<Integer, Integer>> getIndex() {
                return map.entrySet();

            }

        }


    }

}

