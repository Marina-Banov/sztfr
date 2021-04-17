const navigation = {
  top: [
    {
      name: 'Početna',
      url: '/home',
      icon: 'Home',
    },
    /*{
      name: 'UI Elements',
      icon: 'Layers',
      children: [
        {
          name: 'Buttons',
          url: '/elements/buttons',
        },
        {
          name: 'Grid',
          url: '/elements/grid',
        },
        {
          name: 'Alerts',
          url: '/elements/alerts',
        },
        {
          name: 'Typography',
          url: '/elements/typography',
        },
        {
          name: 'Cards',
          url: '/elements/cards',
        },
        {
          name: 'Tabs',
          url: '/elements/tabs',
        },
        {
          name: 'Tables',
          url: '/elements/tables',
        },
        {
          name: 'Forms',
          url: '/elements/forms',
        },
        {
          name: 'Modals',
          url: '/elements/modals',
        },
        {
          name: 'Loaders',
          url: '/elements/loaders',
        },
        {
          name: 'Avatars',
          url: '/elements/avatars',
        },
        {
          name: 'Progress Bars',
          url: '/elements/progressbars',
        },
        {
          name: 'Pagination',
          url: '/elements/pagination',
        },
      ],
    },
    {
      name: 'Pages',
      icon: 'File',
      children: [
        {
          name: 'Blank',
          url: '/pages/blank',
        },
        {
          name: 'Sub Navigation',
          url: '/pages/subnav',
        },
        {
          name: '404',
          url: '/pages/404',
        },
      ],
    },*/
    {
      name: 'Događaji',
      icon: 'Calendar',
      url: '/events'
    },
    {
      name: 'Ankete',
      icon: 'BarChart2',
      url: '/surveys'
    },
    /*{
      name: 'Apps',
      icon: 'Cloud',
      children: [
        {
          name: 'Analytics',
          url: '/apps/analytics',
        },
        {
          name: 'Invoice',
          url: '/apps/invoice',
        },
        {
          name: 'Activity Feed',
          url: '/apps/feed',
        },
        {
          name: 'CMS',
          url: '/apps/cms',
        },
      ],
    },*/
    {
      divider: true,
    },
    {
      name: 'Widgets',
      url: '/widgets',
      icon: 'Package',
      badge: {
        text: 'NEW',
      },
    },
  ],
  bottom: [
    {
      name: 'Account',
      url: '/profile',
      icon: 'User',
      badge: {
        variant: 'success',
        text: '3',
      },
    },
  ],
};

export default navigation
