import React from "react";

function Loading() {
  return (
    <div
      style={{
        height: "100dvh",
        width: "100dvw",
      }}
    >
      <div className="cs-overlay">
        <div className="cs-overlay__content">
          <div className="cs-loader" role="status"></div>
        </div>
      </div>
    </div>
  );
}

export default Loading;
