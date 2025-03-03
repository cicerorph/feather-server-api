import {themes as prismThemes} from 'prism-react-renderer';
import type {Config} from '@docusaurus/types';
import type * as Preset from '@docusaurus/preset-classic';

// This runs in Node.js - Don't use client-side code here (browser APIs, JSX...)

const config: Config = {
  title: 'Feather Dev',
  tagline: '',
  favicon: 'img/favicon.ico',

  // Set the production url of your site here
  url: process.env.DOCS_URL,
  // Set the /<baseUrl>/ pathname under which your site is served
  // For GitHub pages deployment, it is often '/<projectName>/'
  baseUrl: '/',

  // GitHub pages deployment config.
  // If you aren't using GitHub pages, you don't need these.
  organizationName: 'feathermc',
  projectName: 'feather-server-api',

  onBrokenLinks: 'throw',
  onBrokenMarkdownLinks: 'warn',

  // Even if you don't use internationalization, you can use this field to set
  // useful metadata like html lang. For example, if your site is Chinese, you
  // may want to replace "en" with "zh-Hans".
  i18n: {
    defaultLocale: 'en',
    locales: ['en'],
  },

  presets: [
    [
      'classic',
      {
        docs: {
          routeBasePath: '/',
          sidebarPath: './sidebars.ts'
        },
        blog: false,
        theme: {
          customCss: [
            './src/css/custom.css',
            './src/css/font.css',
            './src/css/footer.css',
          ],
        },
      } satisfies Preset.Options,
    ],
  ],

  themeConfig: {
    // Replace with your project's social card
    // image: 'img/docusaurus-social-card.jpg',
    navbar: {
      logo: {
        alt: 'Feather Dev',
        src: 'img/feather-dev-white.svg',
      }
    },
    footer: {
      style: 'dark',
      links: [
        {
          title: 'Docs',
          items: [
            {
              label: 'Server API',
              href: '/'
            }
          ]
        },
        {
          title: 'Community',
          items: [
            {
              label: 'Discord',
              href: 'https://feathermc.com/discord',
            },
            {
              label: 'TikTok',
              href: 'https://feathermc.com/tiktok',
            },
            {
              label: 'Twitter',
              href: 'https://feathermc.com/twitter',
            },
          ],
        }
      ],
      logo: {
        alt: 'Digital Ingot Logo',
        src: 'img/digital-ingot-logo.svg',
        href: 'https://digitalingot.org/'
      },
      copyright: `Copyright © ${new Date().getFullYear()} Digital Ingot, Inc.`,
    },
    prism: {
      theme: prismThemes.github,
      darkTheme: prismThemes.oneDark,
      additionalLanguages: ['java'],
    },
    colorMode: {
      defaultMode: 'dark',
      disableSwitch: true,
      respectPrefersColorScheme: false,
    },
    headTags: [
      {
        tagName: "link",
        attributes: {
          rel: "preload",
          href: "static/fonts/Inter-Regular.woff2",
          as: "font",
          type: "font/woff2",
          crossorigin: "anonymous",
        },
      },
      {
        tagName: "link",
        attributes: {
          rel: "preload",
          href: "static/fonts/Inter-Medium.woff2",
          as: "font",
          type: "font/woff2",
          crossorigin: "anonymous",
        },
      },
      {
        tagName: "link",
        attributes: {
          rel: "preload",
          href: "static/fonts/Inter-Bold.woff2",
          as: "font",
          type: "font/woff2",
          crossorigin: "anonymous",
        },
      },
      {
        tagName: "link",
        attributes: {
          rel: "preload",
          href: "static/fonts/InterDisplay-Bold.woff2",
          as: "font",
          type: "font/woff2",
          crossorigin: "anonymous",
        },
      },
      {
        tagName: "link",
        attributes: {
          rel: "preload",
          href: "static/fonts/IBMPlexMono-Regular.woff2",
          as: "font",
          type: "font/woff2",
          crossorigin: "anonymous",
        },
      },
    ],
  } satisfies Preset.ThemeConfig,

  "plugins": [
    [
      '@docusaurus/plugin-ideal-image',
      {
        quality: 85,
        max: 1200,
        min: 640,
        steps: 4,
        disableInDev: false,
      },
    ],
  ],
};

export default config;
