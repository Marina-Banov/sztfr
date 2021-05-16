const navigation = {
  top: [
    {
      name: "Početna",
      url: "/home",
      icon: "Home",
    },
    /*{
      name: 'UI Elements',
      icon: 'Layers',
      children: [
        {
          name: 'Alerts',
          url: '/elements/alerts',
        },
        {
          name: 'Modals',
          url: '/elements/modals',
        },
        {
          name: 'Loaders',
          url: '/elements/loaders',
        },
      ],
    },*/
    {
      name: "Događaji",
      icon: "Calendar",
      url: "/events",
    },
    {
      name: "Ankete",
      icon: "BarChart2",
      url: "/surveys",
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
          name: 'Activity Feed',
          url: '/apps/feed',
        },
      ],
    },*/
    {
      divider: true,
    },
    {
      name: "Widgets",
      url: "/widgets",
      icon: "Package",
      badge: {
        text: "NEW",
      },
    },
  ],
  bottom: [
    {
      name: "Account",
      url: "/profile",
      icon: "User",
    },
  ],
};

export default navigation;
