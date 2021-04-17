import React from 'react';
import NavSpacer from './NavSpacer';
import NavOverlay from './NavOverlay';
import NavDivider from './NavDivider';
import NavSingleItem from './NavSingleItem';
import NavDropdownItem from './NavDropdownItem';
import PageAlertContext from '../PageAlert/PageAlertContext';
import navigation from "../../../components/home-vibe/navigation";
import Logo from '../../../assets/images/vibe-logo.svg';

export default function SidebarNav({toggleSidebar, isSidebarCollapsed}) {
    const navItems = items => {
      return items.map((item, index) => itemType(item, index));
    };

    const itemType = (item, index) => {
      if (item.children) {
        return <NavDropdownItem key={index} item={item} isSidebarCollapsed={isSidebarCollapsed} />;
      } else if (item.divider) {
        return <NavDivider key={index} />;
      } else {
        return <NavSingleItem item={item} key={index} />;
      }
    };

    const NavBrand = ({ logo, logoText }) => {
      const screenReaderLabel = isSidebarCollapsed ? 'Expand Sidebar Navigation' : 'Collapse Sidebar Navigation';

      return (
        <div className="site-logo-bar">
          <button onClick={toggleSidebar} className="navbar-brand" aria-label={screenReaderLabel}>
            {logo && <img src={logo} alt="" />}
            {logoText && <span className="logo-text">{logoText}</span>}
          </button>
        </div>
      );
    };

    return (
      <PageAlertContext.Consumer>
        {consumer => {
          const hasPageAlertClass = consumer.alert ? 'has-alert' : '';
          return (
            <div>
              <div className={`app-sidebar ${hasPageAlertClass}`}>
                <NavBrand logo={Logo} logoText="SZTFR" />
                <nav>
                  <ul id="main-menu">
                    {navItems(navigation.top)}
                    <NavSpacer />
                    {navItems(navigation.bottom)}
                  </ul>
                </nav>
              </div>
              {isSidebarCollapsed && <NavOverlay onClick={toggleSidebar} />}
            </div>
          );
        }}
      </PageAlertContext.Consumer>
    );
}
