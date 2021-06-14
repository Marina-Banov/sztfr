import React from "react";
import FA from "react-fontawesome";
import {
  Navbar,
  Nav,
  UncontrolledDropdown,
  DropdownToggle,
  DropdownMenu,
  DropdownItem,
  Button,
} from "reactstrap";
import { matchPath } from "react-router-dom";

import { useFirebase } from "appFirebase";
import appRoutes from "appRoutes";
import { Avatar, PageLoader } from "components/common";

export default function Header({ toggleSidebar, isSidebarCollapsed }) {
  const firebase = useFirebase();

  function getPageTitle() {
    let name;
    appRoutes.map((prop) => {
      if (
        matchPath(window.location.pathname, {
          path: prop.path,
          exact: true,
          strict: false,
        })
      ) {
        name = prop.name;
      }
      return null;
    });
    return name;
  }

  return (
    <header className="app-header">
      <SkipToContentLink focusId="primary-content" />
      <div className="top-nav">
        <Navbar color="faded" light expand="md">
          <ToggleSidebarButton
            toggleSidebar={toggleSidebar}
            isSidebarCollapsed={isSidebarCollapsed}
          />
          <div className="page-heading">{getPageTitle()}</div>
          <Nav className="ml-auto" navbar vertical="false">
            <UncontrolledDropdown nav inNavbar>
              <DropdownToggle nav>
                <Avatar
                  size="small"
                  color="#3E408B"
                  initials={firebase.auth.currentUser.email[0]}
                  image={firebase.auth.currentUser.photoURL}
                />
              </DropdownToggle>
              <DropdownMenu right>
                <DropdownItem onClick={() => firebase.logOut()}>
                  Odjava
                </DropdownItem>
              </DropdownMenu>
            </UncontrolledDropdown>
          </Nav>
          <PageLoader />
        </Navbar>
      </div>
    </header>
  );
}

function SkipToContentLink({ focusId }) {
  return (
    <a href={`#${focusId}`} tabIndex="1" className="skip-to-content">
      Skoči na sadržaj
    </a>
  );
}

function ToggleSidebarButton({ isSidebarCollapsed, toggleSidebar }) {
  const chevronClassName = isSidebarCollapsed
    ? "is-collapsed"
    : "is-not-collapsed";
  const screenReaderLabel = isSidebarCollapsed
    ? "Expand Sidebar Navigation"
    : "Collapse Sidebar Navigation";
  return (
    <Button
      onClick={toggleSidebar}
      className={`m-r sidebar-toggle ${chevronClassName}`}
      aria-label={screenReaderLabel}
    >
      <FA name={"bars"} />
    </Button>
  );
}
